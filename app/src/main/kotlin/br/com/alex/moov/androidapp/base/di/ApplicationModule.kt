/*
 *     Copyright 2017 Alex Almeida Tavella
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package br.com.alex.moov.androidapp.base.di

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import br.com.alex.moov.androidapp.email.EmailSender
import br.com.alex.moov.androidapp.email.IntentEmailSender
import br.com.alex.moov.androidapp.logger.AnswersEventLogger
import br.com.alex.moov.androidapp.logger.EventLogger
import br.com.alex.moov.data.CacheDirQualifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(application: Application) {

  private val appContext: Context = application

  @Provides
  @Singleton
  @ApplicationContextQualifier
  internal fun provideAppContext(): Context {
    return appContext
  }

  @Provides
  @Singleton
  internal fun provideSharedPreferences(
      @ApplicationContextQualifier context: Context) = PreferenceManager.getDefaultSharedPreferences(
      context)

  @Provides
  @Singleton
  @CacheDirQualifier
  internal fun provideCacheDirectory(
      @ApplicationContextQualifier context: Context) = context.cacheDir

  @Provides
  @Singleton
  fun provideEmailSender(
      @ApplicationContextQualifier context: Context): EmailSender = IntentEmailSender(context)

  @Provides
  @Singleton
  fun provideEventLogger(): EventLogger = AnswersEventLogger()
}