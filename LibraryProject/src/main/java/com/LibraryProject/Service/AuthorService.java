package com.LibraryProject.Service;
import com.LibraryProject.Configuration.ModelMapperConfig;
import com.LibraryProject.DTO.BookDto;
import com.LibraryProject.Entity.BookEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.LibraryProject.DTO.AuthorDto;
import com.LibraryProject.Entity.AuthorEntity;
import com.LibraryProject.Repo.AuthorRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
    public class AuthorService {

        private final AuthorRepo authorRepo;
        private final ModelMapper modelMapper;

// ModelMapper ke liye object bhi bana denge to chl jayega

        public AuthorDto getAuthorById(Long id) {

            AuthorEntity authorEntity = authorRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Author Not Found" + id));

            return modelMapper.map(authorEntity, AuthorDto.class);
        }

    public AuthorDto createAuthor(AuthorDto authorDto) {

        AuthorEntity authorEntity =
                modelMapper.map(authorDto, AuthorEntity.class);

        authorEntity = authorRepo.save(authorEntity);

        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    public List<AuthorDto> getAllAuthor() {

        return authorRepo.findAll()
                .stream()
                .map(authorEntity ->
                        modelMapper.map(authorEntity, AuthorDto.class))
                .toList();
    }

//    public Page<AuthorDto> getAllAuthor(int page, int size, String sortBy) {
//
//        Pageable pageable = PageRequest.of(
//                page,
//                size,
//                Sort.by(sortBy)
//        );
//
//        return authorRepo.findAll(pageable)
//                .map(authorEntity ->
//                        modelMapper.map(authorEntity, AuthorDto.class));
//    }

    public void deleteAuthor(Long id) {
        AuthorEntity authorEntity = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author Not Found" + id));

         authorRepo.delete(authorEntity);
    }

    public AuthorDto updateAuthorById(Long id, AuthorDto authorDto) {
        AuthorEntity authorEntity = authorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Author Not Found" + id));


        authorEntity.setAuthorName(authorDto.getAuthorName());
        AuthorEntity updatedBook = authorRepo.save(authorEntity);

        return modelMapper.map(updatedBook,AuthorDto.class);


    }
}
