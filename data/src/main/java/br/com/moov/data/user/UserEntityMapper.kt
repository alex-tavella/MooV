package br.com.moov.data.user

import br.com.moov.domain.user.User

class UserEntityMapper {
  fun map(userEntity: UserEntity): User = User(
      userEntity.name)
}