/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.wizardpager;

import com.example.android.wizardpager.wizard.model.AbstractWizardModel;
import com.example.android.wizardpager.wizard.model.BranchPage;
import com.example.android.wizardpager.wizard.model.CustomerInfoPage;
import com.example.android.wizardpager.wizard.model.InstructionPage;
import com.example.android.wizardpager.wizard.model.MultipleFixedChoicePage;
import com.example.android.wizardpager.wizard.model.PageList;
import com.example.android.wizardpager.wizard.model.SingleFixedChoicePage;

import android.content.Context;

public class SandwichWizardModel extends AbstractWizardModel {
	public SandwichWizardModel(Context context) {
		super(context);
	}

	@Override
	protected PageList onNewRootPageList() {
		return new PageList(

				// BranchPage shows all of the branches available: Branch One, Branch Two, Branch Three. Each of these branches
				// have their own questions and the choices of the user will be summarised in the review section at the end
				new BranchPage(this, "I agree to participate to the survey and accept to answer any personal information")
				.addBranch("Accept",
				
						new SingleFixedChoicePage(this, "Where are you now?")
				.setChoices("Hotel ibis Coventry Centre", "Britania Hotel Coventry", "Coventry University", "Coventry Sports & Leisure Centre","Clarks Shoes","The Squirrel","Natiowide","Noise Works","Spire View","Forbidden Planet","Lloyds Bank","Subway","Connells Estate Agents","Natwest Bank","National Express Coventry","Jokers Corner","Budget Car Hire Coventry","Blue Arrow Coventry")
				.setRequired(true),
				
						new SingleFixedChoicePage(this, "What are you doing?")
				.setChoices("Work/Housework", "Preparing Meals/Eating","Taking Medication","Religious Observances","Shopping","Use of Telephone","Community Mobility")
				.setRequired(true),
				
						new SingleFixedChoicePage(this, "How would you rate this place?")
				.setChoices("Poor", "Average", "Good","Excellent","Extraordinary")
				.setRequired(true),
				
						new SingleFixedChoicePage(this, "What is your emotional status")
				.setChoices("Liking", "Joy", "Surprise","Anger","Sadness","Fear")
				.setRequired(true),
				
					new SingleFixedChoicePage(this, "Are you with friends?")
				.setChoices("Yes", "No")
				.setRequired(true),
				
					new SingleFixedChoicePage(this, "How do you feel about this place?")
				.setChoices("Optimism", "Love", "Submission","Aware","Disapproval","Remorse","Contempt","Aggressiveness")
				.setRequired(true),
				
				new SingleFixedChoicePage(this, "Thank You for your response, we are building recommendations.")
				.setChoices("Review Choices")
				.setRequired(true)
						)




				);
	}
}
