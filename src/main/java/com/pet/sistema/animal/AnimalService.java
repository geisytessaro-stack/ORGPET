package com.pet.sistema.animal;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> listarTodos() {
        return animalRepository.findAll();
    }

    public Optional<Animal> buscarPorId(Long id) {
        return animalRepository.findById(id);
    }

    public Animal salvar(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal atualizar(Long id, Animal animalAtualizado) {
        return animalRepository.findById(id)
                .map(animal -> {
                    animal.setNome(animalAtualizado.getNome());
                    animal.setRaca(animalAtualizado.getRaca());
                    animal.setIdade(animalAtualizado.getIdade());
                    animal.setSaude(animalAtualizado.getSaude());
                    animal.setTutor(animalAtualizado.getTutor());
                    return animalRepository.save(animal);
                })
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    public void excluir(Long id) {
        animalRepository.deleteById(id);
    }
}
