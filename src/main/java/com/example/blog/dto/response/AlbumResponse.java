package com.example.blog.dto.response;

import com.example.blog.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class AlbumResponse {
    private Long id;
    private String title;
    private Timestamp createAt;
    private UserDTO user;
}
