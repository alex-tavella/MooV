package br.com.moov.domain.user

interface UserInteractor {
  suspend fun getUser(): User
}

class UserInteractorImpl(private val userRepository: UserRepository): UserInteractor {
  override suspend fun getUser(): User = userRepository.getUser()
}