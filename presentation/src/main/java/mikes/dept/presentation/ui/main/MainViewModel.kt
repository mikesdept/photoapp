package mikes.dept.presentation.ui.main

import mikes.dept.presentation.ui.core.BaseViewModel
import mikes.dept.presentation.ui.core.IViewModel
import javax.inject.Inject

interface MainViewModel : IViewModel

class MainViewModelImpl @Inject constructor() : BaseViewModel(), MainViewModel
