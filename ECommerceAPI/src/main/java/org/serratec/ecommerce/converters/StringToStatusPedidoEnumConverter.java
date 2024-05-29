package org.serratec.ecommerce.converters;

import org.serratec.ecommerce.enums.StatusPedidoEnum;
import org.serratec.ecommerce.exceptions.StatusPedidoInvalidoException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToStatusPedidoEnumConverter implements Converter<String, StatusPedidoEnum> {

    @Override
    public StatusPedidoEnum convert(String source) {
        try {
            return StatusPedidoEnum.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new StatusPedidoInvalidoException(source);
        }
    }
}
