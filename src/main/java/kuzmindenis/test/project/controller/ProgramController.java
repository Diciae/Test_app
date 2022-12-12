package kuzmindenis.test.project.controller;

import kuzmindenis.test.project.dto.ProgramResponse;
import kuzmindenis.test.project.model.Category;
import kuzmindenis.test.project.model.Program;
import kuzmindenis.test.project.service.ProgramService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProgramController {



    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/programs")
    public List<ProgramResponse> getPrograms(
            @RequestParam("page") int page,
            @RequestParam(value = "limit") int limit
    ){
        List<Program> programs = programService.getPrograms(page, limit);
        return programs.stream().map(ProgramController::toProgramResponse).collect(Collectors.toList());
    }

    private static ProgramResponse toProgramResponse(Program program){
        return new ProgramResponse(
                program.getId(),
                program.getName(),
                program.getActionDetails(),
                program.getImage(),
                program.getCategories().stream()
                        .map(Category::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")),
                program.getGoToLink(),
                program.getProductXmlLink()
        );
    }
}
