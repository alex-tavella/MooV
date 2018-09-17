package br.com.moov.domain.user

interface UserRepository {
  fun getUser(): User
}