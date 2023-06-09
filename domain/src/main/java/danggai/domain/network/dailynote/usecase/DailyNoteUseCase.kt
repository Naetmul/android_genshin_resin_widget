package danggai.domain.network.dailynote.usecase

import danggai.domain.core.ApiResult
import danggai.domain.network.dailynote.entity.GenshinDailyNote
import danggai.domain.network.dailynote.entity.HonkaiSrDailyNote
import danggai.domain.network.dailynote.repository.DailyNoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DailyNoteUseCase @Inject constructor(
    private val dailyNoteRepository: DailyNoteRepository
) {
    suspend fun genshin(
        uid: String,
        server: String,
        cookie: String,
        ds: String,
        onStart: () -> Unit,
        onComplete: () -> Unit
    ): Flow<ApiResult<GenshinDailyNote>> =
        dailyNoteRepository.dailyNote(
            uid = uid,
            server = server,
            cookie = cookie,
            ds = ds,
            onStart = onStart,
            onComplete = onComplete
        )

    suspend fun honkaiSr(
        uid: String,
        server: String,
        cookie: String,
        ds: String,
        onStart: () -> Unit,
        onComplete: () -> Unit
    ): Flow<ApiResult<HonkaiSrDailyNote>> =
        dailyNoteRepository.dailyNoteHonkaiSr(
            uid = uid,
            server = server,
            cookie = cookie,
            ds = ds,
            onStart = onStart,
            onComplete = onComplete
        )
}