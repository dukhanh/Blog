package com.example.blog.dto.request;

import com.example.blog.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfile {
    private String firstName;
    private String lastName;
    private String phone;
    private AddressDTO address;
}
