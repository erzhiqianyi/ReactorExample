package com.erzhiqianyi.reactor.repository;

import com.erzhiqianyi.reactor.domain.User;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ReactiveUserRepositoryTest {

    private ReactiveUserRepository reactiveUserRepository;

    @Before
    public void init() {
        Hooks.onOperatorDebug();
        reactiveUserRepository = new ReactiveUserRepository();
    }

    @Test
    public void save() {
    }

    @Test
    public void findFirst() {
        StepVerifier
                .create(reactiveUserRepository.findFirst().log())
                .expectNext(User.SKYLER)
                .expectComplete()
                .verify()
        ;
    }

    @Test
    public void findAll() {
        StepVerifier
                .create(reactiveUserRepository.findAll().log())
                .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById() {
        StepVerifier
                .create(reactiveUserRepository.findById("swhite"))
                .expectNext(User.SKYLER)
                .expectComplete()
                .verify();

    }
}