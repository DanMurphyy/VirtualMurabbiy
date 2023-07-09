package com.hfad.virtualmurabbiy.manager

import com.hfad.virtualmurabbiy.R

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel> = arrayListOf(
        ExerciseModel(1,"Sakrash|Qizib olish", R.drawable.jumping_jacks, false, false ),
        ExerciseModel(2, "Devorga tayanib o'tirish", R.drawable.wall_sit, false, false),
        ExerciseModel(3,"Otjimaniya ", R.drawable.push_up, false, false),
        ExerciseModel(4, "Yotgan holda presga ishlash ", R.drawable.abdominal_crunch, false, false),
        ExerciseModel(5,"Stulga chiqib-tushish ", R.drawable.step_up_onto_chair, false, false),
        ExerciseModel(6, "O'tirib-Turush ", R.drawable.squat, false, false),
        ExerciseModel(7, "Stulda triseps(mushak orti)ga ishlash ", R.drawable.tricep_dip_on_chair, false, false),
        ExerciseModel(8, "Planka holatida turish ", R.drawable.plank, false, false),
        ExerciseModel(9, "Tizzalarni yuqori ko'tarib,joyda yugurish ", R.drawable.high_knees_running_in_place, false, false),
        ExerciseModel(10, "Qadamni oldinga tashlash mashqi", R.drawable.lunge, false, false),
        ExerciseModel(11, "Otjimaniya qilish va navbat bilan aylanish mashqi", R.drawable.push_up_and_rotation, false, false),
        ExerciseModel(12, "Navbat bilan yonga planka turish mashqi", R.drawable.side_plank, false, false)
    )
}