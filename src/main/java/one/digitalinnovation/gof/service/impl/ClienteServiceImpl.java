package one.digitalinnovation.gof.service.impl;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;


    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public String buscarPorId(Long id) {
        try {
            return clienteRepository.findById(id).get().toString();
        } catch (NoSuchElementException e) {
          return  e.getMessage();
        }
    }

    @Override
    public void inserir(Cliente cliente) {
        salvaClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);

        if (clienteBd.isPresent()) {
            salvaClienteComCep(cliente);
        } else {
            clienteRepository.save(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            clienteRepository.delete(clienteBd.get());
        } else {
            System.out.println("Cliente já não existe na base de dados");
        }
    }


    private void salvaClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco enderecoNovo = viaCepService.consultarCep(cep);
            enderecoRepository.save(enderecoNovo);
            return enderecoNovo;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
