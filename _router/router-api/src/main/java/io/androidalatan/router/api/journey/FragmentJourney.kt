package io.androidalatan.router.api.journey

interface FragmentJourney : Journey {
    override fun visit(): FragmentJourney
}