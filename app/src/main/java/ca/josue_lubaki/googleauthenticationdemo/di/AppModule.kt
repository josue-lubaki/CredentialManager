package ca.josue_lubaki.googleauthenticationdemo.di

import ca.josue_lubaki.googleauthenticationdemo.data.datasource.impl.DataStoreOperationsImpl
import ca.josue_lubaki.googleauthenticationdemo.data.repository.DatastoreRepository
import ca.josue_lubaki.googleauthenticationdemo.data.repository.impl.DatastoreRepositoryImpl
import ca.josue_lubaki.googleauthenticationdemo.domain.repository.DataStoreOperations
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.sign_out.SignOutCurrentUserUseCase
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.UseCases
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.read_current_user.ReadCurrentUserUseCase
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.read_current_user_token.ReadCurrentUserTokenUseCase
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.save_current_user.SaveCurrentUserUseCase
import ca.josue_lubaki.googleauthenticationdemo.domain.usecases.save_current_user_token.SaveCurrentUserTokenUseCase
import ca.josue_lubaki.googleauthenticationdemo.presentation.profile.ProfileViewModel
import ca.josue_lubaki.googleauthenticationdemo.presentation.sign_in.SignInViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * created by Josue Lubaki
 * date : 2024-02-26
 * version : 1.0.0
 */

private val dataStoreModule = module {
    single<DataStoreOperations> { DataStoreOperationsImpl(androidContext()) }

    factory<DatastoreRepository> { DatastoreRepositoryImpl(get()) }

    factory<UseCases> {
        UseCases(
            saveCurrentUserTokenUseCase = SaveCurrentUserTokenUseCase(dataStoreRepository = get()),
            readCurrentUserTokenUseCase = ReadCurrentUserTokenUseCase(dataStoreRepository = get()),
            saveCurrentUserUseCase = SaveCurrentUserUseCase(dataStoreRepository = get()),
            readCurrentUserUseCase = ReadCurrentUserUseCase(dataStoreRepository = get()),
            signOutCurrentUserUseCase = SignOutCurrentUserUseCase(datastoreRepository = get())
        )
    }
}

private val utilitiesModule = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
}

private val domainModule = module {
    viewModel<SignInViewModel> {
        SignInViewModel(
            useCases = get(),
            dispatcher = get()
        )
    }

    viewModel<ProfileViewModel> {
        ProfileViewModel(
            useCases = get(),
            googleAuthProvider = get(),
            dispatcher = get()
        )
    }
}

val appModule = listOf(dataStoreModule, domainModule, utilitiesModule)