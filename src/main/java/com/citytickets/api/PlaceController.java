package com.citytickets.api;

import com.citytickets.api.dto.Place;
import com.citytickets.service.PlaceService;
import com.sun.istack.NotNull;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/places")
public final class PlaceController {

    @Autowired
    public PlaceController(PlaceService placeService){
        this.placeService=placeService;
    }

    private final PlaceService placeService;

    @Contract("_ -> new")
    private @NotNull Place placeToDto(@NotNull com.citytickets.repo.model.@org.jetbrains.annotations.NotNull Place placeModel){
        return  Place.builder()
                .id(placeModel.getId())
                .place_type(placeModel.getPlace_type())
                .address(placeModel.getAddress())
                .name(placeModel.getName())
                .description(placeModel.getDescription())
                .opens(placeModel.getOpens())
                .closes(placeModel.getCloses())
                .photo(placeModel.getPhoto())
                .build();
    }

    @GetMapping
    public @NotNull
    @org.jetbrains.annotations.NotNull
    ResponseEntity<List<Place>> index(){
        final List<Place> places = placeService.fetchAll().stream().map(this::placeToDto).collect(Collectors.toList());
        return ResponseEntity.ok(places);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> show(@PathVariable long id){
        try {
            final Place place = placeToDto(placeService.fetchById(id));
            return ResponseEntity.ok(place);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> create(@RequestBody @NotNull @org.jetbrains.annotations.NotNull Place place){

        final String place_type = place.getPlace_type();
        final String address = place.getAddress();
        final String name = place.getName();
        final String description = place.getDescription();
        final Time opens = place.getOpens();
        final Time closes = place.getCloses();
        final String photo = place.getPhoto();
        final long id = placeService.create(place_type, address, name, description, opens, closes, photo);
        final String location = String.format("/places/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> update(@PathVariable long id, @RequestBody @NotNull @org.jetbrains.annotations.NotNull Place place){

        final String place_type = place.getPlace_type();
        final String address = place.getAddress();
        final String name = place.getName();
        final String description = place.getDescription();
        final Time opens = place.getOpens();
        final Time closes = place.getCloses();
        final String photo = place.getPhoto();

        try {
            placeService.update(id, place_type, address, name, description, opens, closes, photo);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> delete(@PathVariable long id){
        placeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
