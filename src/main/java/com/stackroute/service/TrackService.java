package com.stackroute.service;

import com.stackroute.domain.Track;
import com.stackroute.exception.TrackAlreadyExistsException;
import com.stackroute.exception.TrackNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TrackService {
    public Track saveTrack(Track track) throws TrackAlreadyExistsException;
    public List<Track> getAllTrack();
    public boolean deleteTrack (int id) throws TrackNotFoundException;
    public ResponseEntity<Track> updateTrack(Track track, int id) throws TrackNotFoundException;
    public Optional<Track> getTrackById(int id) throws TrackNotFoundException;
}