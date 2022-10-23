package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbUserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
  private final DbUserStorage userStorage;

  public List<User> getFriends(Long userId) {
    User user = userStorage.getById(userId);
//    return userRepository.findUsersBySubscribers(user);

    return userStorage.getAll().stream().filter(u -> u.getSubscriptions().contains(user)).collect(Collectors.toList());
  }

  public void setFriend(Long userId, Long friendId) {
    User user = userStorage.getById(userId);
    User friend = userStorage.getById(friendId);

    //user подписывается на friend
    Set<User> friends = user.getSubscriptions();
    friends.add(friend);
    user.setSubscriptions(friends);
    userStorage.update(user);

    //friend подписывается на user
    Set<User> friendsOfFriend = friend.getSubscriptions();
    friendsOfFriend.add(user);
    friend.setSubscriptions(friendsOfFriend);
    userStorage.update(friend);
  }

  public List<User> getCommonFriends(Long userId, Long otherUserId) {
    List<User> userFriends = getFriends(userId);
    List<User> otherUserFriends= getFriends(otherUserId);

    return userFriends.stream()
            .filter(otherUserFriends::contains)
            .collect(Collectors.toList());
  }

  public void deleteFriend(Long userId, Long friendId) {
    User user = userStorage.getById(userId);
    Set<User> friends = user.getSubscriptions();
    friends.remove(userStorage.getById(friendId));
    userStorage.update(user);
  }
}
