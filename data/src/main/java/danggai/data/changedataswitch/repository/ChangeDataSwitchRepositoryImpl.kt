package danggai.data.changedataswitch.repository

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import danggai.data.changedataswitch.remote.api.ChangeDataSwitchApi
import danggai.domain.base.ApiResult
import danggai.domain.base.Meta
import danggai.domain.changedataswitch.entity.ChangeDataSwitch
import danggai.domain.changedataswitch.repository.ChangeDataSwitchRepository
import danggai.domain.util.Constant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ChangeDataSwitchRepositoryImpl @Inject constructor(
    private val changeDataSwitchApi: ChangeDataSwitchApi,
    private val ioDispatcher: CoroutineDispatcher
) : ChangeDataSwitchRepository {
    override suspend fun changeDataSwitch(
        gameId: Int,
        switchId: Int,
        isPublic: Boolean,
        cookie: String,
        ds: String,
        onStart: () -> Unit,
        onComplete: () -> Unit
    ) = flow<ApiResult<ChangeDataSwitch>> {
        val response = changeDataSwitchApi.changeDataSwitch(
            gameId,
            switchId,
            isPublic,
            cookie,
            ds
        )

        response.suspendOnSuccess {
            emit(ApiResult<ChangeDataSwitch>(
                Meta(this.response.code(), this.response.message()),
                this.response.body()?:ChangeDataSwitch.EMPTY
            ))
        }.suspendOnError {
            emit(ApiResult<ChangeDataSwitch>(
                Meta(this.response.code(), this.response.message()),
                ChangeDataSwitch.EMPTY
            ))
        }.suspendOnException {
            emit(ApiResult<ChangeDataSwitch>(
                Meta(Constant.META_CODE_CLIENT_ERROR, this.exception.message ?: ""),
                ChangeDataSwitch.EMPTY
            ))
        }
    }.onStart{ onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}