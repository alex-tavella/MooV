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

package br.com.alex.moov.androidapp.base.di.about

import android.content.Context
import android.support.v7.app.AppCompatActivity
import br.com.alex.moov.androidapp.about.AboutViewModel
import br.com.alex.moov.androidapp.base.AttachedActivity
import br.com.alex.moov.androidapp.base.di.ActivityModule
import br.com.alex.moov.androidapp.base.di.ApplicationContextQualifier
import dagger.Module
import dagger.Provides

@Module
class AboutModule(val activity: AppCompatActivity) : ActivityModule(activity) {

  @Provides
  fun providesAboutViewModel(@ApplicationContextQualifier context: Context,
      attachedActivity: AttachedActivity) = AboutViewModel(context, attachedActivity)
}