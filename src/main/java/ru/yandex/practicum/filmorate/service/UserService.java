package ru.yandex.practicum.filmorate.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public List<User> getFriends(Long userId) {
    User user = getById(userId);
    return userRepository.findUsersBySubscribers(user);
  }

  public User create(User user) {
    if (user.getName().isEmpty()) {
      user.setName(user.getLogin());
    }
    return userRepository.save(user);
  }

  public User update(User user) {
    User userToUpdate = getById(user.getId());
    userToUpdate = user;
    userRepository.save(userToUpdate);
    return userToUpdate;
  }

  public User getById(@NonNull Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new IllegalStateException("Пользователь не найден"));
  }

  public void setFriend(Long userId, Long friendId) {
    User user = getById(userId);
    User friend = getById(friendId);

    //user подписывается на friend
    Set<User> friends = user.getSubscriptions();
    friends.add(friend);
    user.setSubscriptions(friends);

    //friend подписывается на user
    Set<User> friendsOfFriend = friend.getSubscriptions();
    friendsOfFriend.add(user);
    friend.setSubscriptions(friendsOfFriend);

    userRepository.saveAll(List.of(user,friend));

  }

  public List<User> getCommonFriends(Long userId, Long otherUserId) {
    List<User> userFriends = getFriends(userId);
    List<User> otherUserFriends= getFriends(otherUserId);

    return userFriends.stream()
            .filter(otherUserFriends::contains)
            .collect(Collectors.toList());
  }

  public void deleteFriend(Long userId, Long friendId) {
    User user = getById(userId);
    Set<User> friends = user.getSubscriptions();
    friends.remove(getById(friendId));

    userRepository.save(user);
  }
}
