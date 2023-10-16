package com.kh.product.FORM;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateForm {
    private Long pid;

    @NotBlank(message ="공백비허용")
    private String pname;

    @NotNull(message="공백비허용")
    @Min(value=0,message="음수비허용")
    private Long quantity;

    @NotNull(message="공백비허용")
    @Min(value=1000,message = "최소1000원이상")
    private Long price;
}