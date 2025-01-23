package org.pogonin.authservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ConfirmationCode implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;
    private String code;
    @CreationTimestamp
    private LocalDateTime createTime;

    public ConfirmationCode(User user, String code) {
        this.user = user;
        this.code = code;
    }
}
