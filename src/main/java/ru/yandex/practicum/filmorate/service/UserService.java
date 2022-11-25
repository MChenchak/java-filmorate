package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.impl.UserDao;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService{
  private final UserDao userDao;

  public void addFriend(Long userId, Long friendId) {
    userDao.addFriend(userId, friendId);
  }

  public List<User> getCommonFriends(Long userId, Long otherUserId) {
    return userDao.findCommonFriends(userId, otherUserId);
  }

  public void deleteFriend(Long userId, Long friendId) {
    userDao.deleteFriend(userId, friendId);
  }

  public List<User> getFriends(Long userId) {
    return userDao.getFriends(userId);
  }
}
