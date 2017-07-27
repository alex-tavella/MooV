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

package br.com.alex.moov.androidapp.about

import android.databinding.DataBindingUtil
import android.os.Bundle
import br.com.alex.moov.R
import br.com.alex.moov.androidapp.base.BaseActivity
import br.com.alex.moov.androidapp.base.di.ApplicationComponent
import br.com.alex.moov.androidapp.base.di.about.AboutComponent
import br.com.alex.moov.androidapp.base.di.about.AboutModule
import br.com.alex.moov.androidapp.logger.EventLogger
import br.com.alex.moov.databinding.ActivityAboutBinding
import javax.inject.Inject


class AboutActivity : BaseActivity() {

  lateinit var aboutComponent: AboutComponent

  @Inject lateinit var aboutViewModel: AboutViewModel

  @Inject lateinit var eventLogger: EventLogger

  val mBinding: ActivityAboutBinding by lazy {
    DataBindingUtil.setContentView<ActivityAboutBinding>(this,
        R.layout.activity_about)
  }

  override fun createViewModel() = aboutViewModel

  override fun injectDependencies(applicationComponent: ApplicationComponent) {
    aboutComponent = applicationComponent.plus(AboutModule(this))
    aboutComponent.inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_about)

    mBinding.viewModel = aboutViewModel

    eventLogger.logContentView("About Screen")
  }
}