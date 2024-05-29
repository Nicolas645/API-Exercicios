package org.serratec.ecommerce.services;

import org.serratec.ecommerce.configurations.NumverifyConfig;
import org.serratec.ecommerce.dtos.NumverifyResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NumverifyService {

    @Autowired
    private NumverifyConfig numverifyConfig;

    @Autowired
    private RestTemplate restTemplate;

    public NumverifyResponseDTO validatePhoneNumber(String phoneNumber, String countryCode) {
        String url = UriComponentsBuilder.fromHttpUrl(numverifyConfig.getUrl())
                .queryParam("access_key", numverifyConfig.getAccessKey())
                .queryParam("number", phoneNumber)
                .queryParam("country_code", countryCode)
                .queryParam("format", 1)
                .toUriString();

        return restTemplate.getForObject(url, NumverifyResponseDTO.class);
    }
}
