package br.com.moov.data.user

import br.com.moov.domain.user.User
import br.com.moov.domain.user.UserRepository

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
    private val userEntityMapper: UserEntityMapper) : UserRepository {

  override fun getUser(): User = userEntityMapper.map(userDataSource.getUser())
}