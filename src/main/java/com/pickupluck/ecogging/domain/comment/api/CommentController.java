package com.pickupluck.ecogging.domain.comment.api;

import com.pickupluck.ecogging.domain.comment.dto.CommentResponse;
import com.pickupluck.ecogging.domain.comment.dto.CommentSaveRequest;
import com.pickupluck.ecogging.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * todo:
 * - Exception Handler
 * - Custom Exception
 * - Custom Response<T>(data)
 */
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity saveComment(@RequestBody CommentSaveRequest commentSaveRequest) throws Exception {
        commentService.save(commentSaveRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) throws Exception {
        commentService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/comments/me")
    public ResponseEntity<List<CommentResponse>> getMyComment() throws Exception {
        return new ResponseEntity<>(
                commentService.getMyComments(),
                HttpStatus.OK
        );
    }
}
