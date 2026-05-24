import { createApi } from '@reduxjs/toolkit/query/react'
import { axiosBaseQuery } from '@/config/axiosBaseQuery'
import type { AuditLog } from '@/types/api'
import type { ResponsePage } from '@/types/common'

export interface AuditLogParams {
  userId?: string
  action?: string
  status?: string
  page?: number
  size?: number
}

export const auditLogApi = createApi({
  reducerPath: 'auditLogApi',
  baseQuery: axiosBaseQuery(),
  tagTypes: ['AuditLog'],
  endpoints: (builder) => ({
    getAuditLogs: builder.query<ResponsePage<AuditLog>, AuditLogParams>({
      query: ({ page = 0, size = 20, ...rest } = {}) => ({
        url: '/logs',
        method: 'GET',
        params: { page, size, ...rest }
      }),
      transformResponse: (response: ResponsePage<AuditLog>) => response,
      providesTags: ['AuditLog']
    })
  })
})

export const { useGetAuditLogsQuery } = auditLogApi
