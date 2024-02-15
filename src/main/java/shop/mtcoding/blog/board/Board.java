package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Table(name = "board_tb")
@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String author;
    @Column(length = 20)
    private String title;
    @Column(length = 20)
    private String content;

    private LocalDateTime createdAt;
}
