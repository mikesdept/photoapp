package mikes.dept.presentation.ui.photocreate

import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModel
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModelImpl
import javax.inject.Inject

interface PhotoCreateViewModel : NavDirectionsViewModel

class PhotoCreateViewModelImpl @Inject constructor() : NavDirectionsViewModelImpl(), PhotoCreateViewModel
