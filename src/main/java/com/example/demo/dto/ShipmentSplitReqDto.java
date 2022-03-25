package com.example.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author liangxu
 * @version 1.0
 * @description
 * @since 2022-03-17 17:30:40
 **/
@Data
@Accessors(chain = true)
public class ShipmentSplitReqDto {

    @Size(min = 1, max = 10)
    private List<Double> splitWeightList;

    @NotEmpty(message = "courierNumber is not null")
    private String courierNumber;

}