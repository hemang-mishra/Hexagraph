package com.hexagraph.pattagobhi.ui.screens.chat


sealed class BotComponent {
//    data class MenuState(
//        val id: String,
//        val selectedOption: MenuOptions
//    ) : BotComponent()

    data class UserResponseState(
        val id: String,
        val response: String
    ) : BotComponent()

    data class GeminiResponseState(
        val id: String,
        val response: String
    ) : BotComponent()

    data class GeminiState(
        val id: String,
        val prompt: String
    ) : BotComponent()

//    data class DECarouselState(
//        val id: String,
//        val selectedOption: DataEntryCarouselEntity
//    ) : BotComponent()
//
//    data class DEExerciseTypeCarouselState(
//        val id: String,
//        val selectedOption: ExerciseType
//    ) : BotComponent()
//
//    data class DEExerciseDurationState(
//        val id: String,
//        val duration: TimeEntity
//    ) : BotComponent()
//
//    data class DEExerciseAddOneMoreState(
//        val id: String,
//        val shouldAdd: Boolean
//    ) : BotComponent()
//
//    data class DESleepDurationState(
//        val id: String,
//        val duration: WakeSleepEntity
//    ) : BotComponent()
//
//    data class DEMedicineSearchState(
//        val id: String,
//        val medicine: String,
//    ) : BotComponent()
//
//    data class DEMedicineTimeState(
//        val id: String,
//        val time: MedicineTime
//    ) : BotComponent()
//
//    data class DEMedicineFrequencyIsDaily(
//        val id: String,
//        val isDaily: Boolean
//    ) : BotComponent()
//
//    data class DEMedicineWeekdaysState(
//        val id: String,
//        val frequency: List<MedicineFrequency>
//    ) : BotComponent()
//
//    data class DEMedicineNotificationState(
//        val id: String,
//        val shouldNotify: Boolean
//    ) : BotComponent()
//
//    data class DEMedicineNotificationTimeState(
//        val id: String,
//        val time: TimeEntity
//    ) : BotComponent()
//
//    data class DEMedicineAddOneMoreState(
//        val id: String,
//        val shouldAdd: Boolean
//    ) : BotComponent()
//
//    data class DEStatsCarouselState(
//        val id: String,
//        val selectedOption: StatsEntity
//
//    ) : BotComponent()
//
//    data class DEStatsWeightEntryState(
//        val id: String,
//        val weight: Float
//    ) : BotComponent()
//
//    data class DEStatsBloodPressureSystolicEntryState(
//        val id: String,
//        val systolic: Int
//    ) : BotComponent()
//
//    data class DEStatsBloodPressureDiastolicEntryState(
//        val id: String,
//        val diastolic: Int
//    ) : BotComponent()
//
//    data class DEStatsGlucoseTestTypeState(
//        val id: String,
//        val glucoseTestType: GlucoseTestType
//    ) : BotComponent()
//
//
//    data class DEStatsGlucoseEntryState(
//        val id: String,
//        val glucose: Int
//    ) : BotComponent()
//
//    data class DEMealTrackState(
//        val id: String,
//        val meal: String
//    ) : BotComponent()
//
//    data class DEWaterTrackState(
//        val id: String,
//        val water: Int
//    ) : BotComponent()
//
//    data class AppmtList(
//        val id: String,
//        val appList: AppointmentMenu
//    ): BotComponent()
//
//    data class AppmtTime(
//        val id: String,
//        val time: TimeEntity
//    ): BotComponent()
//
//    data class AppmtDate(
//        val id: String,
//        val date: DateEntity
//    ): BotComponent()
//
//    data class AppmtDoctor(
//        val id: String,
//        val doctor: String
//    ): BotComponent()
//
//    data class AppmtSpeciality(
//        val id: String,
//        val speciality: DoctorProfession
//    ): BotComponent()
//
//    data class MoodState(
//        val id: String,
//        val mood: String
//    ): BotComponent()
//
//    data class AppointmentHistoryState(
//        val id: String,
//        val appointmentHistory: String
//    ): BotComponent()
//
//    data class UpcomingAppointmentsState(
//        val id: String,
//        val upcomingAppointments: String
//    ): BotComponent()

}

//data class GeminiData(
//    val selectedCarouselEntity: DataEntryCarouselEntity? = null,
//    val selectedExerciseType: ExerciseType? = null,
//    val exerciseDuration: TimeEntity? = null,
//    val shouldAddExercise: Boolean? = null,
//    val caloriesBurnt: Int? = null,
//    val sleepDuration: WakeSleepEntity? = null,
//    val medicineName: String? = null,
//    val medicineTime: MedicineTime? = null,
//    val isMedicineDaily: Boolean? = null,
//    val medicineFrequency: List<MedicineFrequency>? = null,
//    val shouldNotifyForMedicine: Boolean? = null,
//    val medicineNotifcationTime: TimeEntity? = null,
//    val shouldAddMedicine: Boolean? = null,
//    val selectedStatsEntity: StatsEntity? = null,
//    val weight: Float? = null,
//    val systolicPressure: Int? = null,
//    val diastolicPressure: Int? = null,
//    val glucoseTestType: GlucoseTestType? = null,
//    val glucoseLevel: Int? = null,
//    val meal: String? = null,
//    val water: Int? = null,
//    val appList: AppointmentMenu? = null,
//    val appTime: TimeEntity? = null,
//    val appDate: DateEntity? = null,
//    val appDoctor: String? = null,
//    val appSpeciality: DoctorProfession? = null,
//    val mood: String? = null,
//    val appointmentHistory: String? = null,
//    val upcomingAppointments: String? = null
//)


enum class BotUiState(val stateSerialNo: Int){
    MENU(1),
    GEMINI(2),
    DE_CAROUSEL_STATE(3),
    DE_EXERCISE_TYPE_CAROUSEL_STATE(4),
    DE_EXERCISE_DURATION_STATE(5),
    DE_EXERCISE_ADD_ONE_MORE_STATE(6),
    DE_SLEEP_DURATION_STATE(7),
    DE_MEDICINE_SEARCH_STATE(8),
    DE_MEDICINE_TIME(9),
    DE_MEDICINE_FREQUENCY_IS_DAILY(10),
    DE_MEDICINE_WEEKDAYS_STATE(11),
    DE_MEDICINE_NOTIFICATION_STATE(12),
    DE_MEDICINE_NOTIFICATION_TIME_STATE(13),
    DE_MEDICINE_ADD_ONE_MORE_STATE(14),
    DE_STATS_CAROUSEL_STATE(15),
    DE_STATS_WEIGHT_ENTRY_STATE(16),
    DE_STATS_BLOOD_PRESSURE_SYSTOLIC_ENTRY_STATE(17),
    DE_STATS_BLOOD_PRESSURE_DIASTOLIC_ENTRY_STATE(18),
    DE_STATS_GLUCOSE_TEST_TYPE_STATE(19),
    DE_STATS_GLUCOSE_ENTRY_STATE(20),
    DE_MEAL_TRACK_STATE(21),
    DE_WATER_TRACK_STATE(22),
    APPOINTMENT_LIST(23),
    APPOINTMENT_TIME(24),
    APPOINTMENT_DATE(25),
    APPOINTMENT_DOCTOR(26),
    APPOINTMENT_SPECIALITY(27),
    MOOD(28),
    APPOINTMENT_HISTORY(29),
    UPCOMING_APPOINTMENTS(30)
}