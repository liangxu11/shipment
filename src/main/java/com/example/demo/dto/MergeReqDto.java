package com.example.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @param * @Param null:
 * @author liangxu
 * @description
 * @date 2022-03-16 13:11:16
 * @return * @return: null
 **/
@Data
@Accessors(chain = true)
public class MergeReqDto {

    @Size(max = 10, min = 1)
    private List<String> courierNumberList;


}