package com.eventostec.api.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.address.AddressRequestDTO;
import com.eventostec.api.exception.ResourceNotFoundException;
import com.eventostec.api.repositories.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(AddressRequestDTO data){
        Address newAddress = new Address();
        newAddress.setCity(data.city());
        newAddress.setUf(data.uf());
        addressRepository.save(newAddress);
        return newAddress;
    }

    public Address findyByEventId(UUID eventId){
        return addressRepository.findyByEventId(eventId).orElseThrow(() -> new ResourceNotFoundException("Endereco nao encontrado"));
    }
}
