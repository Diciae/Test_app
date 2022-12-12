package kuzmindenis.test.project.service;

import kuzmindenis.test.project.model.Program;
import kuzmindenis.test.project.repository.ProgramRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public void saveProgram(Program program){
        programRepository.save(program);
    }

    public List<Program> getPrograms(int page, int limit) {
        return programRepository.findAll((PageRequest.of(page, limit))).getContent();
    }
}
