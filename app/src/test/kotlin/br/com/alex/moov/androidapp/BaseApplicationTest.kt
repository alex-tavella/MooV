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

package br.com.alex.moov.androidapp

import android.content.Context
import android.support.annotation.CallSuper
import br.com.alex.moov.BaseTest
import br.com.alex.moov.androidapp.base.AttachedActivity
import br.com.alex.moov.androidapp.base.di.DaggerTestApplicationComponent
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import javax.inject.Inject

open class BaseApplicationTest : BaseTest() {

  protected val testApplicationComponent by lazy {
    DaggerTestApplicationComponent.builder()
        .build()!!
  }

  @Inject
  protected lateinit var appContext: Context

  @Inject
  protected lateinit var attachedActivity: AttachedActivity

  @CallSuper
  @Before
  open fun setUp() {
    MockitoAnnotations.initMocks(this)
    Mockito.reset(appContext)
  }

  init {
    testApplicationComponent.inject(this)
  }
}