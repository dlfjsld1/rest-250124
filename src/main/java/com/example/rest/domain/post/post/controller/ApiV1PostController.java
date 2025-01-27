package com.example.rest.domain.post.post.controller;

import com.example.rest.domain.post.post.dto.PostDto;
import com.example.rest.domain.post.post.entity.Post;
import com.example.rest.domain.post.post.service.PostService;
import com.example.rest.global.entity.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller와 @ResponseBody 어노테이션을 합친 기능
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {

    private final PostService postService;

    @GetMapping
    @ResponseBody
    public RsData<List<PostDto>> getItems() {

        List<Post> posts = postService.getItems();

        List<PostDto> postDtos =  posts.stream()
                .map(PostDto::new)
                .toList();
        return new RsData<>("200", "글 조회가 완료되었습니다.", postDtos);

    }


    @GetMapping("{id}")
    public RsData<PostDto> getItem(
            @PathVariable
            long id
    ) {
        Post post = postService.getItem(id).get();
        PostDto postDto = new PostDto(post);
        return new RsData<>("200", "글 조회가 완료되었습니다.", postDto);
    }


    record WriteReqBody(
            @NotBlank
            @Length(min=3)
            String title,
            @NotBlank
            @Length(min=3)
            String content) {};

    @PostMapping
    public RsData write(
            @RequestBody
            @Valid
            WriteReqBody body
    ) {
        postService.write(body.title(), body.content());
        return new RsData<>(
                "200-1",
                "글 작성이 완료되었습니다."
        );
    }


    record ModifyReqBody(
            @NotBlank
            @Length(min=3)
            String title,
            @NotBlank
            @Length(min=3)
            String content) {}

    @PutMapping("{id}")
    public RsData modify(
            @PathVariable
            long id,
            @RequestBody
            @Valid
            ModifyReqBody body
    ) {
        Post post = postService.getItem(id).get();
        postService.modify(post, body.title(), body.content());
        return new RsData<>(
                "200-1",
                "%d번 글 수정이 완료되었습니다.".formatted(id)
        );
    }


    @DeleteMapping("/{id}")
    public RsData delete(@PathVariable long id) {
        Post post = postService.getItem(id).get();
        postService.delete(post);

        return new RsData(
                "200-1",
                "%d번 글 삭제가 완료되었습니다.".formatted(id)
        );
    }
}
