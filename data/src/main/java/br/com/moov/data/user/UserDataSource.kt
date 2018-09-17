package br.com.moov.data.user

interface UserDataSource {
  fun getUser(): UserEntity
}