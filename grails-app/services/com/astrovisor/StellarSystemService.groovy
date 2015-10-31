package com.astrovisor

import grails.transaction.Transactional

@Transactional
class StellarSystemService {

    StellarSystem insertOrUpdate(StellarSystem stellarSystem) {
        stellarSystem.save()
        return stellarSystem
    }

    void delete(StellarSystem stellarSystem) {
        stellarSystem.delete()
    }

    def getStellarSystems(int offset, int max) {
        return StellarSystem.list([offset: offset, max:max])
    }
}
