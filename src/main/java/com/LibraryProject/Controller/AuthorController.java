package com.LibraryProject.Controller;

import com.LibraryProject.DTO.AuthorDto;
import com.LibraryProject.Repo.AuthorRepo;
import com.LibraryProject.Service.AuthorService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;


    @GetMapping("/{id}")
    public AuthorDto getAuthor(@PathVariable Long id) {

        return authorService.getAuthorById(id);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthor() {
        return ResponseEntity.ok(authorService.getAllAuthor());
    }



//    @GetMapping
//    public ResponseEntity<Page<AuthorDto>> getAllAuthor(@RequestParam (defaultValue = "0")int page,
//                                                        @RequestParam (defaultValue = "5")int size ,
//                                                        @RequestParam(defaultValue = "BookTitle")String sortBy){
//        return ResponseEntity.ok(authorService.getAllAuthor(page ,size,sortBy));
//    }


    @PostMapping
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto) {
            return authorService.createAuthor(authorDto);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(
            @PathVariable Long id,
            @RequestBody AuthorDto authorDto) {

        return ResponseEntity.ok(
                authorService.updateAuthorById(id, authorDto)
        );
    }

    }

