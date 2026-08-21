package com.pet.sistema.vacina;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacinaService {

    private final VacinaRepository vacinaRepository;

    public VacinaService(VacinaRepository vacinaRepository) {
        this.vacinaRepository = vacinaRepository;
    }

    public List<Vacina> listarTodas() {
        return vacinaRepository.findAll();
    }

    public Optional<Vacina> buscarPorId(Long id) {
        return vacinaRepository.findById(id);
    }

    public Vacina salvar(Vacina vacina) {
        return vacinaRepository.save(vacina);
    }

    public Vacina atualizar(Long id, Vacina vacinaAtualizada) {
        return vacinaRepository.findById(id)
                .map(vacina -> {
                    vacina.setNome(vacinaAtualizada.getNome());
                    vacina.setDataVacina(vacinaAtualizada.getDataVacina());
                    vacina.setAnimal(vacinaAtualizada.getAnimal());
                    return vacinaRepository.save(vacina);
                })
                .orElseThrow(() -> new RuntimeException("Vacina não encontrada"));
    }

    public void excluir(Long id) {
        vacinaRepository.deleteById(id);
    }
}
