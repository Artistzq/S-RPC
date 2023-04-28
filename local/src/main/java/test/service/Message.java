package test.service;

import lombok.*;

import java.io.Serializable;

/**
 * @Author : Artis Yao
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Message implements Serializable {
    private String description;
    private String content;
}
