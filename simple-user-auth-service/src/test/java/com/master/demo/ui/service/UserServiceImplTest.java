/**
 * 
 */
package com.master.demo.ui.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.master.demo.io.entities.UserEntity;
import com.master.demo.io.repositories.UserRepository;
import com.master.demo.shared.dto.UserDTO;
import com.master.demo.ui.service.impl.UserServiceImpl;

/**
 * @author george
 *
 *
 *         class under test: UserServiceImpl
 */
class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userService;
	@Mock
	UserRepository userRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test method for
	 * {@link com.master.demo.ui.service.impl.UserServiceImpl#getUser(java.lang.String)}.
	 */
	@Test
	void testGetUSer() {
		UserEntity userEntity = new UserEntity();
		userEntity.setUserID("hjdsahjkladasdg");
		userEntity.setFirstName("Nahashon");
		userEntity.setEncPassword("adfjskfadskjfld;j;ds");

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
//		instantiate the test 
		UserDTO user = userService.getUser("test@test.com");
//	 test the object to see if it contains the expected result 
//	 use assertNotNull
		assertNotNull(user);
//		or assertEquals
		assertEquals("Nahashon", user.getFirstName());
	}

	@Test
	final void getUser_UserNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
//Executing the method getUser should throw exception UserNotFoundException
//		Handle exception by assertThrows(exceptionClass.class, function that ones run and throws the exception)
		
//		if the code is changed to throw another exception that we are not expecting, the test fails.

		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUser("test@test.com");
		});
	}
}
