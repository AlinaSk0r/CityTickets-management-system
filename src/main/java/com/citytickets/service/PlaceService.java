package com.citytickets.service;

import com.citytickets.repo.PlaceRepo;
import com.citytickets.repo.model.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class PlaceService {

    private final PlaceRepo placeRepo;

    public List<Place> fetchAll(){
        return placeRepo.findAll();
    }

    public Place fetchById(long id)throws IllegalArgumentException{
        final Optional<Place> maybePlace = placeRepo.findById(id);

        if (maybePlace.isEmpty()) throw new IllegalArgumentException("Place not found");
        else return maybePlace.get();
    }

    public long create(String place_type, String address, String name, String description, Time opens, Time closes, String photo){
        final Place place = new Place(place_type, address, name, description, opens, closes, photo);
        final Place savedPlace = placeRepo.save(place);

        return savedPlace.getId();
    }

    public void update(Long id, String place_type, String address, String name, String description, Time opens, Time closes, String photo) throws IllegalArgumentException{
        final Optional<Place> maybePlace = placeRepo.findById(id);
        if (maybePlace.isEmpty()) throw new IllegalArgumentException("Place not found");
        final Place place = maybePlace.get();
        if (place_type != null && !place_type.isBlank()) place.setPlace_type(place_type);
        if (address != null && !address.isBlank()) place.setAddress(address);
        if (name != null && !name.isBlank()) place.setName(name);
        if (description != null && !description.isBlank()) place.setDescription(description);
        if (opens != null) place.setOpens(opens);
        if (closes != null) place.setCloses(closes);
        if (photo != null && !photo.isBlank()) place.setPhoto(photo);

        placeRepo.save(place);
    }

    public void delete(long id)throws IllegalArgumentException{
        placeRepo.deleteById(id);
    }
}
