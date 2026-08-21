package com.pet.sistema.tutor;

import com.pet.sistema.tutor.Tutor;
import com.pet.sistema.tutor.TutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public List<Tutor> listarTodos() {
        return tutorRepository.findAll();
    }

    public Optional<Tutor> buscarPorId(Long id) {
        return tutorRepository.findById(id);
    }

    public Tutor salvar(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    public Tutor atualizar(Long id, Tutor tutorAtualizado) {
        return tutorRepository.findById(id)
                .map(tutor -> {
                    tutor.setNome(tutorAtualizado.getNome());
                    tutor.setTelefone(tutorAtualizado.getTelefone());
                    return tutorRepository.save(tutor);
                })
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));
    }

    public void excluir(Long id) {
        tutorRepository.deleteById(id);
    }
}