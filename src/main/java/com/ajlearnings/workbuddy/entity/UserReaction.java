package com.ajlearnings.workbuddy.entity;

import com.ajlearnings.workbuddy.enums.CommentType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "user_reaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReaction extends BaseEntity implements Serializable {
    @NonNull
    private Boolean isLiked;
    @DBRef
    private Comment comment;
    @DBRef
    private User user;
}
