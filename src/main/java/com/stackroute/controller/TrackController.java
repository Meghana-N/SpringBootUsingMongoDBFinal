package com.stackroute.controller;
import com.stackroute.domain.Track;
import com.stackroute.exception.TrackAlreadyExistsException;
import com.stackroute.exception.TrackNotFoundException;
import com.stackroute.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1")
public class TrackController {

    private TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService)
    {
        this.trackService=trackService;
    }

    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) throws TrackAlreadyExistsException
    {
//        ResponseEntity responseEntity;
//        trackService.saveTrack(track);
//        responseEntity=new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);
//        return responseEntity;
        ResponseEntity responseEntity;
        try
        {
            trackService.saveTrack(track);
            responseEntity=new ResponseEntity<String>("Successfully Created", HttpStatus.CREATED);
        }
        catch( Exception ex)
        {
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("tracks")
    public ResponseEntity<List<Track>> getAllTrack()
    {
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<List<Track>>(trackService.getAllTrack(),HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("/track/{id}")
    public ResponseEntity<?> updateTrack(@RequestBody Track track, @PathVariable int id) throws TrackNotFoundException {
//        ResponseEntity responseEntity;
//        trackService.updateTrack(track,id);
//        responseEntity=new ResponseEntity<String>("Successfully Updated", HttpStatus.ACCEPTED);
//        return responseEntity;
        ResponseEntity responseEntity;
        try
        {
            trackService.updateTrack(track,id);
            responseEntity=new ResponseEntity<String>("Successfully Updated", HttpStatus.ACCEPTED);
        }
        catch( Exception ex)
        {
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/track/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable String id) throws TrackNotFoundException {
//        ResponseEntity responseEntity;
//        trackService.deleteTrack(Integer.parseInt(id));
//        responseEntity=new ResponseEntity<String>("Successfully deleted",HttpStatus.OK);
//        return responseEntity;
        ResponseEntity responseEntity;
        try {
            trackService.deleteTrack(Integer.parseInt(id));
            responseEntity=new ResponseEntity<String>("Successfully deleted",HttpStatus.OK);
        }
        catch (Exception ex)
        {
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping(value = "/track/{id}")
    public ResponseEntity<?> getByIdTrack(@PathVariable int id) throws TrackNotFoundException {
//        ResponseEntity responseEntity;
//        return new ResponseEntity<Optional<Track>>(trackService.getTrackById(id),HttpStatus.OK);
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<Optional<Track>>(trackService.getTrackById(id),HttpStatus.OK);
        }
        catch (Exception ex)
        {
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
}