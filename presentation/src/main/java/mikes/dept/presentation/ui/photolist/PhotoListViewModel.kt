package mikes.dept.presentation.ui.photolist

import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModel
import mikes.dept.presentation.ui.core.navdirections.NavDirectionsViewModelImpl
import javax.inject.Inject

interface PhotoListViewModel : NavDirectionsViewModel

class PhotoListViewModelImpl @Inject constructor() : NavDirectionsViewModelImpl(), PhotoListViewModel
