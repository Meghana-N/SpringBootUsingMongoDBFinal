package com.stackroute.service;

import com.stackroute.domain.Track;
import com.stackroute.exception.TrackAlreadyExistsException;
import com.stackroute.exception.TrackNotFoundException;
import com.stackroute.repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TrackServiceImplTest {

    private static final int TRACK_ID = 150;
    private Track track;

    //Create a mock for UserRepository
    @Mock
    private TrackRepository trackRepository;

    //Inject the mocks as dependencies into UserServiceImpl
    @InjectMocks
    private TrackServiceImpl trackService;
    List<Track> list= null;


    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        track = new Track();
        track.setTrackId(TRACK_ID);
        track.setTrackName("Wert");
        track.setTrackComments("Jenny the composer");
        list = new ArrayList<>();
        list.add(track);
    }

    @Test
    public void saveUserTestSuccess() throws TrackAlreadyExistsException {
        when(trackRepository.save((Track)any())).thenReturn(track);
        Track savedUser = trackService.saveTrack(track);
        Assert.assertEquals(track,savedUser);
        //verify here verifies that userRepository save method is only called once
        verify(trackRepository,times(1)).save(track);
    }

    @Test(expected = TrackAlreadyExistsException.class)
    public void saveUserTestFailure() throws TrackAlreadyExistsException {
        when(trackRepository.save((Track) any())).thenReturn(track);
        when(trackRepository.existsById(TRACK_ID)).thenReturn(true);
        Track savedUser = trackService.saveTrack(track);
        Assert.assertEquals(track,savedUser);
    }

    @Test
    public void getAllUserSuccess(){
        trackRepository.save(track);
        //stubbing the mock to return specific data
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> userlist = trackService.getAllTrack();
        Assert.assertEquals(list,userlist);
    }

    @Test
    public void deleteTrackTestSuccess() throws TrackNotFoundException {
        trackRepository.save(track);
        when(trackRepository.existsById(TRACK_ID)).thenReturn(true);
        Boolean status = trackService.deleteTrack(TRACK_ID);
        verify(trackRepository,times(1)).deleteById(TRACK_ID);
        Assert.assertEquals(true,status);
    }

    @Test(expected = TrackNotFoundException.class)
    public void deleteTrackTestFailure() throws TrackNotFoundException {
        trackRepository.save(track);
        when(trackRepository.existsById(140)).thenReturn(false);
        Boolean status = trackService.deleteTrack(TRACK_ID);
        verify(trackRepository,times(1)).deleteById(TRACK_ID);
        Assert.assertEquals(false,status);
    }

    @Test
    public void updateTrackTestSuccess() throws TrackNotFoundException {
        Track updatedTrack=new Track();
        updatedTrack.setTrackId(TRACK_ID);
        updatedTrack.setTrackName("abc");
        updatedTrack.setTrackComments("ghj");

        when(trackRepository.save(track)).thenReturn(updatedTrack);
        when(trackRepository.existsById(TRACK_ID)).thenReturn(true);
        when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.of(track));
        assertEquals(new ResponseEntity<Track>(updatedTrack, HttpStatus.OK),trackService.updateTrack(updatedTrack,TRACK_ID));
    }

    @Test(expected = TrackNotFoundException.class)
    public void updateTrackTestFailure() throws TrackNotFoundException {
        Track updatedTrack=new Track();
        updatedTrack.setTrackId(TRACK_ID);
        updatedTrack.setTrackName("abc");
        updatedTrack.setTrackComments("ghj");

        when(trackRepository.save(track)).thenReturn(updatedTrack);
        when(trackRepository.existsById(TRACK_ID)).thenReturn(false);
        when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.of(track));
        assertEquals(new ResponseEntity<Track>(updatedTrack, HttpStatus.OK),trackService.updateTrack(updatedTrack,TRACK_ID));
    }

    @Test
    public void getTrackByIdTestSuccess() throws TrackNotFoundException {
        when(trackRepository.existsById(TRACK_ID)).thenReturn(true);
        when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.of(track));
        Optional<Track> expectedValues = Optional.ofNullable(track);
        Optional<Track> actualValues = trackService.getTrackById(TRACK_ID);
        Assert.assertEquals(expectedValues,actualValues);
    }

    @Test(expected = TrackNotFoundException.class)
    public void getTrackByIdTestFailure() throws TrackNotFoundException {
        when(trackRepository.existsById(TRACK_ID)).thenReturn(false);
        when(trackRepository.findById(TRACK_ID)).thenReturn(Optional.of(track));
        Optional<Track> expectedValues = Optional.ofNullable(track);
        Optional<Track> actualValues = trackService.getTrackById(TRACK_ID);
        Assert.assertEquals(expectedValues,actualValues);
    }
}

