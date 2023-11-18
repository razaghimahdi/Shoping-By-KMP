package business.interactors.splash


import business.constants.AUTHORIZATION_BEARER_TOKEN
import business.constants.DataStoreKeys
import business.core.AppDataStore
import business.core.DataState
import business.core.ProgressBarState
import business.core.UIComponent
import business.util.handleUseCaseException
import business.datasource.network.splash.SplashService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginInteractor(
    private val service: SplashService,
    private val appDataStoreManager: AppDataStore,
) {


    fun execute(
        email: String,
        password: String,
    ): Flow<DataState<String>> = flow {

        try {

            emit(DataState.Loading(progressBarState = ProgressBarState.ButtonLoading))

            val apiResponse = service.login(email, password)


            println("AppDebug LoginInteractor apiResponse:"+apiResponse)

             apiResponse.alert?.let { alert ->
                emit(
                    DataState.Response<String>(
                        uiComponent = UIComponent.Dialog(
                            alert = alert
                        )
                    )
                )
            }


            val result = apiResponse.result


            if (result != null) {
                appDataStoreManager.setValue(
                    DataStoreKeys.TOKEN,
                    AUTHORIZATION_BEARER_TOKEN + result
                )
            }


            emit(DataState.Data(result, apiResponse.status))

        } catch (e: Exception) {
            println("AppDebug LoginInteractor e:"+e.message)
            e.printStackTrace()
            emit(handleUseCaseException(e))

        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }


    }


}