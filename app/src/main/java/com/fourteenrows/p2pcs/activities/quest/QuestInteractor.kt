package com.fourteenrows.p2pcs.activities.quest

import com.fourteenrows.p2pcs.activities.quest.QuestContractor.CompleteListener
import com.fourteenrows.p2pcs.activities.quest.QuestContractor.Interactor

class QuestInteractor(private val listener: CompleteListener) : Interactor